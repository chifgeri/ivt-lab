package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockPrimary;
  private TorpedoStore mockSecondary;

  @BeforeEach
  public void init(){
    this.mockPrimary = mock(TorpedoStore.class);
    this.mockSecondary = mock(TorpedoStore.class);
    this.ship = new GT4500(mockPrimary, mockSecondary);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mockPrimary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockSecondary.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(mockPrimary, times(1)).fire(1);
    verify(mockSecondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_primaryWasFired_secondaryFire(){
      //Arrange
      when(mockPrimary.fire(1)).thenReturn(true);
      when(mockSecondary.fire(1)).thenReturn(true);

      ship.fireTorpedo(FiringMode.SINGLE);
      boolean result = ship.fireTorpedo(FiringMode.SINGLE);

      // Assert
      assertEquals(true, result);
      verify(mockPrimary, times(1)).fire(1);
      verify(mockSecondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_single_primaryEmpty(){
    when(mockPrimary.isEmpty()).thenReturn(true);
    when(mockSecondary.fire(1)).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(true, result);

    verify(mockPrimary, times(1)).isEmpty();
    verify(mockSecondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_primaryWasFired_secondaryEmpty(){
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockSecondary.fire(1)).thenReturn(true);
    when(mockSecondary.isEmpty()).thenReturn(true);

    ship.fireTorpedo(FiringMode.SINGLE);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(true, result);
    verify(mockPrimary, times(2)).fire(1);

  }

  @Test
  public void fireTorpedo_single_allStoreEmpty(){
    when(mockPrimary.isEmpty()).thenReturn(false);
    when(mockSecondary.isEmpty()).thenReturn(false);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_all_secondaryEmpty(){
    when(mockPrimary.isEmpty()).thenReturn(false);
    when(mockSecondary.isEmpty()).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.ALL);

    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_single_primaryFailed(){
    when(mockPrimary.fire(1)).thenReturn(false);
    when(mockSecondary.fire(1)).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(false, result);
    verify(mockSecondary, times(0)).fire(1);


  }

  @Test
  public void fireTorpedo_sigle_primaryFired_bothStoresEmpty(){
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockSecondary.isEmpty()).thenReturn(true);
    when(mockPrimary.isEmpty()).thenReturn(false);

    ship.fireTorpedo(FiringMode.SINGLE);

    when(mockPrimary.isEmpty()).thenReturn(true);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(false, result);


  }


}
